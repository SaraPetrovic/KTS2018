package ftn.kts.transport.services;

import java.io.File;

public interface ConductorService {

	File generateQrCode(Long id);
	Long decodeId(String encodedID);
}
