class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')

        "/stock/$ticker?"(controller: 'stock')

        "/chat"(controller: 'chat', action: 'index')
        "/chat/listen"(controller: 'chat', action: 'listen')
        "/chat/login"(controller: 'chat', action: 'login')
        "/chat/post"(controller: 'chat', action: 'post')

        "/"(controller: 'chat', action: 'index')
	}
}
