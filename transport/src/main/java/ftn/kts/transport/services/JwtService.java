package ftn.kts.transport.services;

import ftn.kts.transport.model.User;

public interface JwtService {

	String generate(User user);
	User validate(String token);
}
