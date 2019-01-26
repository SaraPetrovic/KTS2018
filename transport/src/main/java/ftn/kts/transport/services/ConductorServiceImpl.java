package ftn.kts.transport.services;

import java.io.File;

import org.springframework.stereotype.Service;

import com.google.common.io.BaseEncoding;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

@Service
public class ConductorServiceImpl implements ConductorService {

	@Override
    public File generateQrCode(Long id) {

        String encodedID = BaseEncoding.base64()
                .encode(("TicketID=" + id.toString()).getBytes());

        File qrCode = QRCode.from(encodedID).to(ImageType.JPG).withSize(250, 250).file();

        return qrCode;
    }

	@Override
    public Long decodeId(String encodedID){

	    byte[] decodedID = BaseEncoding.base64()
                .decode(encodedID);

	    String stringID = new String(decodedID);

	    return Long.parseLong(stringID.substring(9));
    }

}
