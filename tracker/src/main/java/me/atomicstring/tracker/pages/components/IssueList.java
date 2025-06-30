package me.atomicstring.tracker.pages.components;

import java.util.List;

import static j2html.TagCreator.*;
import j2html.tags.ContainerTag;
import me.atomicstring.tracker.dao.data.Issue;
import me.atomicstring.tracker.pages.dsl.Component;

public class IssueList implements Component {
	
	List<Issue> issueList;

	public IssueList(List<Issue> issueList) {
		this.issueList = issueList;
	}

	@Override
	public ContainerTag<?> build() {
		return div();
	}

}
