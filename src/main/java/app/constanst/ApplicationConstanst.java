package app.constanst;

import java.security.KeyStore;

public class ApplicationConstanst {

	public static class PathConstanst {
		public static final String PATH = "classpath:export_auth.bks";
		public static final String EXPORT = "C:/keystore/export_auth.bks";
	}
	
	public static final String KEYSTORE_FILE_PASSWORD = "tKJJEjmLYcmeGv6rKzWBXgLx8pqdvnhzWD8tM6Pp82trm9FGjdTS7ve5ve9At3KZwJhUEX8pAbDxVUP5KyMGEDnKH7VLZxCm6w6A5PzvQNCByggTsFbV9qwtm35bqh9k";
	
	public static class KEYSTORE_ALIAS{
		
		public static KEY_ALIAS_DETAIL PUBLIC_KEY_ALIAS = new KEY_ALIAS_DETAIL("PUBLIC_KEY_ALIAS", "pE2zd3Z5TWyaBBASgFcdeH7H5vAF3zDf4fsS83NRPbDfp4JwkMza6gHLnThf4x7HDpsng55GRjz5c8rnwA8tus4FaWLQkJpzWELPksn2ALHCvXARayUuPNYBnngxGfGe");
		public static KEY_ALIAS_DETAIL BCRYPT_SALT_ALIAS = new KEY_ALIAS_DETAIL("BCRYPT_SALT_ALIAS", "6Hj8qbPrzy5bE2Z4Rgu3xVzKpPcPAj4gCYeDZETaPNX5rNJ8UNL3zbpyBsd2AS9PGYhjf2jUr7PGx9E3M2VW39keb97CSRgasajx2u3gbGNys4c4VXQzHMEKLnXePQf6");
		public static KEY_ALIAS_DETAIL SECRET_KEY_ALIAS = new KEY_ALIAS_DETAIL("SECRET_KEY_ALIAS", "WNMUgtsRYKsHvkNGvcweJDyPFsMMYu2ySDhKWzcLVv5fEHyKwEA5TJvqZ72BcTYF8SgFhjh4qXUExhZSyrzgZEgBC8NrCpGn7NSZJLEJAqeHh5rF7AyW6E9jCNkRMR4J");
		
		public static class KEY_ALIAS_DETAIL{
			
			private String alias;
			private KeyStore.ProtectionParameter protection;
			public KEY_ALIAS_DETAIL(String alias, String protectionPassword ) {
				this.alias = alias;
				this.protection = new KeyStore.PasswordProtection(protectionPassword.toCharArray());
			}
			public String getAlias() {
				return alias;
			}
			public KeyStore.ProtectionParameter getProtection() {
				return protection;
			}
			
		}
	}
}
