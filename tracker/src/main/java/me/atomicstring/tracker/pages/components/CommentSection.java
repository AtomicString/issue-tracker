package me.atomicstring.tracker.pages.components;

import static j2html.TagCreator.*;

import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.PrettyTime;

import j2html.tags.ContainerTag;
import me.atomicstring.tracker.App;
import me.atomicstring.tracker.dao.UserDao;
import me.atomicstring.tracker.dao.data.Comment;
import me.atomicstring.tracker.pages.dsl.Component;
import me.atomicstring.tracker.users.User;

public class CommentSection implements Component {
	
	List<Comment> comments;
	
	public CommentSection(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public ContainerTag<?> build() {
		UserDao userDao = App.jdbi.onDemand(UserDao.class);
		PrettyTime prettyTime = new PrettyTime();
		return div(each(comments, comment -> {
			User user = userDao.getUserById(comment.getAuthorId());
			return div(
					div(user.getUsername()),
					div(comment.getContent()).withClass("border border-zinc-300 rounded-xl p-1"),
					div(span(prettyTime.format(Date.from(comment.getCreatedAt()))).withClass("text-zinc-500 float-right"))
				).withClasses("mt-5");
		}));
	}

}
