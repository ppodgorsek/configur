package com.github.ppodgorsek.configur.springdata.jpa;

/**
 * Metadata for the Spring Data JPA module.
 *
 * @author Paul Podgorsek
 */
public final class JpaConfigurationMetadata {

	public static final int MAX_RESULTS = 50;

	/**
	 * Table column names.
	 */
	public final class Column {

		/**
		 * Prefix allowing to avoid issues with reserved keywords across databases.
		 */
		public static final String COLUMN_PREFIX = "c_";

		public static final String DESCRIPTION = COLUMN_PREFIX + "description";
		public static final String ID = COLUMN_PREFIX + "id";
		public static final String KEY = COLUMN_PREFIX + "key";
		public static final String NAME = COLUMN_PREFIX + "name";
		public static final String PARENT = COLUMN_PREFIX + "parent";
		public static final String VALUE = COLUMN_PREFIX + "value";

		/**
		 * Default private constructor to avoid instantiating this class.
		 */
		private Column() {
			super();
		}
	}

	/**
	 * Database constraints.
	 */
	public final class Constraint {

		public static final int DESCRIPTION_LENGTH = 2048;
		public static final int ID_LENGTH = 36;
		public static final int KEY_LENGTH = 128;
		public static final int NAME_LENGTH = 256;
		public static final int VALUE_LENGTH = 128;

		/**
		 * Default private constructor to avoid instantiating this class.
		 */
		private Constraint() {
			super();
		}
	}

	/**
	 * Table names.
	 */
	public final class Table {

		/**
		 * Prefix allowing to avoid issues with reserved keywords across databases.
		 */
		public static final String TABLE_PREFIX = "config_";

		public static final String CONFIGURATION_CATEGORY = TABLE_PREFIX + "category";
		public static final String CONFIGURATION_PROPERTY = TABLE_PREFIX + "property";

		/**
		 * Default private constructor to avoid instantiating this class.
		 */
		private Table() {
			super();
		}
	}

	/**
	 * Default private constructor to avoid instantiating this class.
	 */
	private JpaConfigurationMetadata() {
		super();
	}

}
