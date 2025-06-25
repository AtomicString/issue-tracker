package me.atomicstring.tracker.pages.dsl;

public enum PageStages {
	
	HEAD,
	NAVBAR,
	CONTENT,
	FOOTER,
	END;
		
	static public final PageStages[] values = values();
	
	public PageStages prev() {
		return values[Math.max(0,(ordinal() - 1))];
	}

	public PageStages next() {
		return values[Math.min(values.length-1,(ordinal() + 1))];
	}
}
