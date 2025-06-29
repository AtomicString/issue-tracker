package me.atomicstring.tracker.pages.dsl;

import static j2html.TagCreator.*;

import j2html.tags.ContainerTag;
import j2html.tags.DomContent;
import j2html.tags.specialized.HtmlTag;

public class PageBuilder {
	
	HtmlTag working = html();
	ContainerTag<?> head = head();
	ContainerTag<?> body = body();
	ContainerTag<?> navbar = nav();
	ContainerTag<?> content = div();
	ContainerTag<?> footer = div();
	
	PageStages stage = PageStages.HEAD;
	
	boolean titleAdded = false;
	
	void guard(PageStages stage) {
		if (this.stage != stage) {
			throw new IllegalStateException("Not at " + stage + " stage");
		}
	}
	
	public PageBuilder bodyClass(String... classes) {
		body.withClasses(classes);
		return this;
	}

	public PageBuilder nextStage(String... classes) {
		switch(stage) {
			case HEAD:
				working.with(head.withClasses(classes));
				break;
			case NAVBAR:
				body.with(navbar.withClasses(classes));
				break;
			case CONTENT:
				body.with(content.withClasses(classes));
				break;
			case FOOTER:
				body.with(footer.withClasses(classes));
				working.with(body);
				break;
			case END:
				throw new IllegalStateException("No more stages after END");
		}
		stage = stage.next();
		return this;
	}

	public PageBuilder skipStage() {
		switch(stage) {
			case HEAD:
				working.with(head);
			case NAVBAR:
			case CONTENT:
				break;
			case FOOTER:
				working.with(body);
				break;
			case END:
				throw new IllegalStateException("No more stages after END");
		}
		stage = stage.next();
		return this;
	}
	
	public PageBuilder addScript(String script) {
		guard(PageStages.HEAD);
		head.with(script().withSrc(script));
		return this;
	}
	
	public PageBuilder addCss(String url) {
		guard(PageStages.HEAD);
		head.with(link().withRel("stylesheet").withHref(url));
		return this;
	}
	
	public PageBuilder addTitle(String pageTitle) {
		guard(PageStages.HEAD);
		if (titleAdded) throw new IllegalStateException("title already added");
        this.titleAdded = true;
		head.with(title("Faultline | " 	+ pageTitle));
		return this;
	}
	
	public PageBuilder attachComponent(Component component, String... classes) {
		switch(stage) {
			case HEAD:
				head.with(component.build().withClasses(classes));
				break;
			case NAVBAR:
				navbar.with(component.build().withClasses(classes));
				break;
			case CONTENT:
				content.with(component.build().withClasses(classes));
				break;
			case FOOTER:
				footer.with(component.build().withClasses(classes));
				break;
			case END:
				throw new IllegalStateException("Cannot insert component at END stage");
		}
		return this;
	}
	
	public PageBuilder addNavItem(DomContent content, Links link) {
		guard(PageStages.NAVBAR);
		navbar.with(link.build().with(content));
		return this;
	}
	
	public HtmlTag build() {
		return working;
	}
}
