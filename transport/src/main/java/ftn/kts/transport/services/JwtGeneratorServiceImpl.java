package ftn.kts.transport.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.kts.transport.exception.TokenValidationException;
import ftn.kts.transport.model.User;
import ftn.kts.transport.security.JwtGenerator;
import ftn.kts.transport.security.JwtValidator;

@Service
public class JwtGeneratorServiceImpl implements JwtGeneratorService {

	@Autowired
	private JwtGenerator jwtGenerator;
	@Autowired
	private JwtValidator jwtValidator;
	
	@Override
	public String generate(User user) {
		return jwtGenerator.generate(user);
	}

	@Override
	public User validate(String token) {
		return jwtValidator.validate(token);
//		if (ret == null) {
//			throw new TokenValidationException("Sorry! Token cannot be validated");
//		}
//		return ret;
	}

	
}
