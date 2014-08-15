package br.com.caelum.vraptor.twirl;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import play.twirl.api.Html;

@Controller
public class IndexController {

	@Get("/")
	public Html index() {
		return html.simple.apply("oi", "oi");
	}
}
