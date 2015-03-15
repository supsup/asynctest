package com.burtbeckwith.async

import javax.servlet.AsyncContext

// based on https://gist.github.com/1621658
class StockController {

	private static final String YAHOO_FINANCE_API = "http://download.finance.yahoo.com/d/quotes.csv?s="
	private static final String API_PARAMS = "&f=nsl1op&e=.csv"

	def index(String ticker) {
		AsyncContext asyncContext = startAsync()
		asyncContext.start {

			ticker = ticker ?: 'GOOG'

			try {
				Double price = new URL(YAHOO_FINANCE_API + ticker + API_PARAMS).text.split(',')[-1] as Double
				asyncContext.response.writer.printf 'ticker: %s, price: %.2f', ticker, price
			}
			finally {
				asyncContext.complete()
			}
		}
	}
}
