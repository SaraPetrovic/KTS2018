package ftn.kts.transport.services;

import ftn.kts.transport.model.User;

public interface JwtGeneratorService {

	String generate(User user);
	User validate(String token);
}
