package org.csstudio.nams.service.configurationaccess.localstore.declaration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.csstudio.nams.common.contract.Contract;

/**
 * Dieses Daten-Transfer-Objekt stellt hält die Konfiguration eines Zieltopics
 * für den TobicConnector.
 * 
 * Das Create-Statement für die Datenbank hat folgendes Aussehen:
 * 
 * <pre>
 * CREATE TABLE &quot;AMS_TOPIC&quot; 
 * (
 * 	 &quot;ITOPICID&quot; NUMBER(11,0) NOT NULL ENABLE, 
 *   &quot;IGROUPREF&quot; NUMBER(11,0) DEFAULT -1 NOT NULL ENABLE, 
 *   &quot;CTOPICNAME&quot; VARCHAR2(128), 
 *   &quot;CNAME&quot; VARCHAR2(128), 
 *   &quot;CDESCRIPTION&quot; VARCHAR2(256), 
 *   
 *   PRIMARY KEY (&quot;ITOPICID&quot;) ENABLE
 * )
 * </pre>
 */
@Entity
@Table(name = "AMS_TOPIC")
public class TopicDTO {

	@Column(name = "CDESCRIPTION")
	private String description;

	@Column(name = "CNAME")
	private String name;
	@Column(name = "CTOPICNAME")
	private String topicName;

	@Id
	@GeneratedValue
	@Column(name = "ITOPICID")
	private int id = -1;

	@Column(name = "IGROUPREF")
	private int groupRef;

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TopicDTO)) {
			return false;
		}
		final TopicDTO other = (TopicDTO) obj;
		if (this.description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!this.description.equals(other.description)) {
			return false;
		}
		if (this.groupRef != other.groupRef) {
			return false;
		}
		if (this.id != other.id) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.topicName == null) {
			if (other.topicName != null) {
				return false;
			}
		} else if (!this.topicName.equals(other.topicName)) {
			return false;
		}
		return true;
	}

	public String getDescription() {
		return this.description;
	}

	public int getGroupRef() {
		return this.groupRef;
	}

	/**
	 * Die DB-Id. Wird automatisch erzeugt und ist daher nur lesbar.
	 * 
	 * @return -1, wenn der Datensatz nicht eingerichtet ist, >0 bei gültiger
	 *         Id.
	 */
	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getTopicName() {
		return this.topicName;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((this.description == null) ? 0 : this.description.hashCode());
		result = prime * result + this.groupRef;
		result = prime * result + this.id;
		result = prime * result
				+ ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result
				+ ((this.topicName == null) ? 0 : this.topicName.hashCode());
		return result;
	}

	/**
	 * 
	 * @param ame
	 *            Die Beschreibung des Topics; 256 Zeichen oder kürzer.
	 */
	public void setDescription(final String description) {
		Contract.require(description.length() <= 256,
				"topicName.length() <= 256");

		this.description = description;
	}

	public void setGroupRef(final int groupRef) {
		this.groupRef = groupRef;
	}

	@SuppressWarnings("unused")
	private void setId(final int id) {
		this.id = id;
	}

	/**
	 * 
	 * @param ame
	 *            Der Name/Identifizierung des Topics; 128 Zeichen oder kürzer.
	 */
	public void setName(final String name) {
		Contract.require(name.length() <= 128, "topicName.length() <= 128");

		this.name = name;
	}

	/**
	 * 
	 * @param topicName
	 *            Der Name des Topics; 128 Zeichen oder kürzer.
	 */
	public void setTopicName(final String topicName) {
		Contract
				.require(topicName.length() <= 128, "topicName.length() <= 128");

		this.topicName = topicName;
	}

	@Override
	public String toString() {
		final StringBuilder resultBuilder = new StringBuilder("TopicDTO: ");
		resultBuilder.append(this.getTopicName());
		resultBuilder.append(" (");
		resultBuilder.append(this.getId());
		resultBuilder.append("), ");
		resultBuilder.append(this.getName());
		resultBuilder.append(", ");
		resultBuilder.append(this.getGroupRef());
		resultBuilder.append(", ");
		resultBuilder.append(this.getDescription());
		return resultBuilder.toString();
	}

}
