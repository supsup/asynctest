package com.burtbeckwith.async

import javax.servlet.AsyncContext
import javax.servlet.AsyncEvent

// based on https://blogs.oracle.com/enterprisetechtips/entry/asynchronous_support_in_servlet_3
class ChatController {

	static allowedMethods = [login: 'POST', post: 'POST']

	private static final String JUNK = '<!-- Comet is a programming technique that enables web servers to send data to the client without having any need for the client to request it. -->\n'
	private static final long TIMEOUT = 10 * 60 * 1000 // 10 minutes
	private static final char CR = '\r' as char

	private static final Map ESCAPED = [
		('\b' as char): '\\b',
		('\f' as char): '\\f',
		('\n' as char): '<br />',
		('\t' as char): '\\t',
		('\'' as char): "\\'",
		('\"' as char): '\\\"',
		('\\' as char): '\\\\',
		('<'  as char): '&lt;',
		('>'  as char): '&gt;',
		('&'  as char): '&amp;']

	// dependency injected
	ChatManager chatManager

	// dependency injected
	AsyncLogger asyncLogger

	def beforeInterceptor = {
		response.setHeader 'Cache-Control', 'private'
		response.setHeader 'Pragma', 'no-cache'
	}

	def index() {
		// show the html page
	}

	// set as the url of the iframe
	def listen() {
		response.characterEncoding = 'UTF-8'
		response.contentType = 'text/html'

		// for Safari, Chrome, IE and Opera
		PrintWriter writer = response.writer
		10.times { writer.write(JUNK) }
		writer.flush()

		AsyncContext ac = startAsync()
		ac.timeout = TIMEOUT
		ac.addListener asyncLogger

		ac.addListener new AsyncListenerAdapter() {
			void onComplete(AsyncEvent event) { chatManager.remove ac }
			void onTimeout(AsyncEvent event)  { chatManager.remove ac }
			void onError(AsyncEvent event)    { chatManager.remove ac }
		}

		chatManager.register ac
	}

	def login(String name) {
		notify 'System Message', name + ' has joined.'
		render text: 'success', contentType: 'text/plain'
	}

	def post(String name, String message) {
		notify name, message
		render text: 'success', contentType: 'text/plain'
	}

	private void notify(String name, String message) {
		chatManager.notify '<script type="text/javascript">\n' + toJsonp(name, message) + '</script>\n'
	}

	private String escape(String s) {
		StringBuilder buffer = new StringBuilder(s.length())
		s.length().times { i ->
			char c = s.charAt(i)
			if (c != CR) {
				buffer << (ESCAPED[c] ?: c)
			}
		}
		buffer.toString()
	}

	private String toJsonp(String name, String message) {
		'window.parent.app.update({ name: "' + escape(name) + '", message: "' + escape(message) + '" });\n'
	}
}
