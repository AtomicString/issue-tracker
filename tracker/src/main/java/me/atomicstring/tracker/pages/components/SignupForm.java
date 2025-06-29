package me.atomicstring.tracker.pages.components;

import static j2html.TagCreator.*;
import j2html.tags.ContainerTag;
import me.atomicstring.tracker.pages.dsl.Component;

public class SignupForm implements Component {

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
					
					div("Confirm Password")
					.withClasses("text-zinc-700", "mt-3"),
					
					input()
					.withType("password")
					.withName("re_password")
					.withPlaceholder("Confirm Password")
					.withClasses("border-slate-500", "p-2", "rounded-xl", "border-2"),
					
					div("Profile Picture")
					.withClasses("text-zinc-700", "mt-3"),

					input()
					.withType("file")
					.withName("image")
					.withAccept("image/png, image/jpeg"),
					
					div().withClasses("border-b-2", "border-b-slate-700 mt-5"),
					
					div(
						input()
						.withType("submit")
						.withValue("Sign Up")
						.withClasses("border-sky-500", "p-1", "rounded-xl", "border-2", "cursor-pointer", "hover:bg-sky-500", "transition", "duration-200", "ease-in-out")
					).withClasses("mt-4"),

					div(join(
						"Already have an account? ", 
						
						a("Log in")
						.withHref("/login")
						.withClasses("text-blue-600")
						
					)).withClasses("text-md", "text-slate-700", "mt-4")
					
				).withAction("/signup").withEnctype("multipart/form-data").withMethod("POST")
			);
	}

}
