package me.atomicstring.tracker.pages.components;

import static j2html.TagCreator.*;
import j2html.tags.ContainerTag;
import me.atomicstring.tracker.pages.dsl.Component;

public class LoginForm implements Component {

	@Override
	public ContainerTag<?> build() {
		return div(
				form(
					div("Username")
					.withClasses("text-zinc-700"),
					
					input()
					.withType("text")
					.withName("username")
					.withPlaceholder("Username")
					.withClasses("border-slate-500", "p-2", "rounded-xl", "border-2"),
					
					div("Password")
					.withClasses("text-zinc-700", "mt-3"),
					
					input()
					.withType("password")
					.withName("password")
					.withPlaceholder("Password")
					.withClasses("border-slate-500", "p-2", "rounded-xl", "border-2"),
					
					div().withClasses("border-b-2", "border-b-slate-700 mt-5"),
					
					div(
						input()
						.withType("submit")
						.withValue("Log In")
						.withClasses("border-sky-500", "p-1", "rounded-xl", "border-2", "cursor-pointer", "hover:bg-sky-500", "transition", "duration-200", "ease-in-out")
					).withClasses("mt-4"),

					div(join(
						"Don't have an account? ", 
						
						a("Sign up")
						.withHref("/signup")
						.withClasses("text-blue-600")
						
					)).withClasses("text-md", "text-slate-700", "mt-4")
					
				).withAction("/login").withMethod("POST")
			);
	}

}
