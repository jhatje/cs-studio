package org.csstudio.nams.service.configurationaccess.localstore.declaration;

public final class TopicConfigurationId {

	public static TopicConfigurationId valueOf(final int id) {
		return new TopicConfigurationId(id);
	}

	private final int id;

	private TopicConfigurationId(final int id) {
		this.id = id;
		// TODO Auto-generated constructor stub
	}

	public int asDatabaseId() {
		return this.id;
	}
}
