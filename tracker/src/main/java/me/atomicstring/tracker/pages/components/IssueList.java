package me.atomicstring.tracker.pages.components;

import static j2html.TagCreator.*;

import java.util.List;

import j2html.tags.ContainerTag;
import j2html.tags.DomContent;
import me.atomicstring.tracker.dao.data.Issue;
import me.atomicstring.tracker.pages.dsl.Component;

public class IssueList implements Component {
	
	List<Issue> issueList;

	public IssueList(List<Issue> issueList) {
		this.issueList = issueList;
	}

	@Override
	public ContainerTag<?> build() {
		return div(table(
				tr(
					td("Issue").withClasses("w-5/12 p-2 border"),
					td("Status").withClasses("w-3/12 p-2 text-center border"),
					td("Tags").withClasses("w-4/12 p-2 text-center border")
				),
				each(issueList, issue -> {
					DomContent status;
					switch(issue.getStatus()) {
						case "open":
							status = td(span("In-Progress").withClasses("bg-green-400/60 p-2 rounded-xl")).withClass("border text-center");
							break;
						case "in-progress":
							status = td(span("In-Progress").withClasses("bg-yellow-300/60 p-2 rounded-xl")).withClass("border text-center");
							break;
						case "closed":
							status = td(span("Closed").withClasses("bg-zinc-400/60 p-2 rounded-xl")).withClass("border text-center");
							break;
						default:
							status = null;
					}
					return tr(
							td(a(issue.getTitle()).withHref("/issue/" + issue.getId())).withClasses("p-2", "border"),
							status,
							td().withClasses("p-2", "border")
						);
				})
			).withClasses("w-full m-2 border border-collapse rounded-xl"));
	}

}
