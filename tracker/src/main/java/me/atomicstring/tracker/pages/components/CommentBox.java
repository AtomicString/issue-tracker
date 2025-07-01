package me.atomicstring.tracker.pages.components;

import static j2html.TagCreator.div;
import static j2html.TagCreator.form;
import static j2html.TagCreator.input;

import java.util.UUID;

import j2html.tags.ContainerTag;
import me.atomicstring.tracker.pages.dsl.Component;

public class CommentBox implements Component {
	
	public CommentBox(UUID issueId) {
		this.issueId = issueId;
	}

	UUID issueId;

	@Override
	public ContainerTag<?> build() {
		return div(
				form(
					div("New Comment").withClass("text-xl"),
					
					div(input()
					.withType("text")
					.withName("comment")
					.withClass("border-2 border-zinc-300 p-2 w-full rounded-xl")),
					
					input().withType("hidden").withName("issue").withValue(issueId.toString()),

					div(input()
					.withType("submit")
					.withValue("Post")
					.withClass("border-2 border-green-400 rounded-xl hover:bg-green-400 transition duration-200 ease-in-out p-2 mt-1 cursor-pointer"))
					
				).withAction("/new-comment").withMethod("POST")
			).withClass("mt-5");
	}

}
