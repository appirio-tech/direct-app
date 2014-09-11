class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		
		name logoutAction: "/logout" {
			controller = 'scorecard'
			action = 'logout'
		}
		
		"/"(view:"/index")
		"500"(view:'/error')
	}
}
