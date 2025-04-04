package backend.repository;

import backend.content.Content;

public class ContentRepository {
	private int MAX_CONTENT;
	private Content[] contentList;
	private int contentCount;

	public ContentRepository(int maxNumOfContentEntries) {
		if (maxNumOfContentEntries <= 0)
			throw new IllegalArgumentException("ERROR: Max number of entries provided cannot be 0 or negative");
		contentCount = 0;
		this.MAX_CONTENT = maxNumOfContentEntries;
		this.contentList = new Content[MAX_CONTENT];
	}

	public boolean addContent(Content content) {
		if (content == null)
			throw new IllegalArgumentException("ERROR: Content provided is null");
		if (contentCount < MAX_CONTENT) {
			this.contentList[contentCount++] = content;
			return true;
		} else {
			throw new NullPointerException(
					"Max content count reached. Please remove an entry before entering another.");
		}
	}

	public Content findContentById(int contentId) {
		if (contentId < 0)
			throw new IllegalArgumentException("ERROR: ID Cannot be negative");
		int i = 0;
		for (i = 0; i < contentList.length; i++) {
			if (contentList[i].getContentId() == contentId)
				return contentList[i];
		}
		throw new NullPointerException("ERROR: No content with that ID was found");
	}

	public Content findContentByTitle(String title) {
		if (title == null || title.trim().isEmpty())
			throw new IllegalArgumentException("ERROR: Title cannot be null or empty");
		int i = 0;
		for (i = 0; i < contentList.length; i++) {
			if ((contentList[i].getTitle()).equals(title))
				return contentList[i];
		}
		throw new IllegalArgumentException("ERROR: Content with this title could not be found");
	}

	public Content[] getAllContent() {
		Content[] allContents = new Content[contentCount];
		int j = 0;
		for (Content i : contentList) {
			allContents[j++] = i;
			if (j == contentCount)
				break;
		}
		return allContents;
	}

}
