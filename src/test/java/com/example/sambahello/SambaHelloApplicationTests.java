package com.example.sambahello;

import jcifs.CIFSContext;
import jcifs.CIFSException;
import jcifs.CloseableIterator;
import jcifs.Configuration;
import jcifs.config.PropertyConfiguration;
import jcifs.context.BaseContext;
import jcifs.smb.NtlmPasswordAuthenticator;
import jcifs.smb.SmbFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SambaHelloApplicationTests {

    @Test
    void connectToDocker() throws MalformedURLException, CIFSException, IOException {
        Properties prop = new Properties();
        prop.put( "jcifs.smb.client.useExtendedSecurity","false" );
        prop.put( "jcifs.smb.client.useSMB2Negotiation", "true" );
        Configuration config = new PropertyConfiguration(prop);
        CIFSContext baseContext = new BaseContext(config);


        CIFSContext contextWithCred = baseContext.withCredentials(new NtlmPasswordAuthenticator(null, "bob", "bobspasswd"));
        SmbFile share = new SmbFile("smb://localhost:445/mnt", contextWithCred);
        boolean a = share.exists();

        CloseableIterator c = share.children();

        int b = 0;
    }

    @Test
    void connectToReal() throws MalformedURLException, CIFSException, IOException {
        String domain = "192.168.1.103";
        Properties prop = new Properties();
        prop.put( "jcifs.smb.client.useExtendedSecurity","false" );
        prop.put( "jcifs.smb.client.useSMB2Negotiation", "true" );
        Configuration config = new PropertyConfiguration(prop);
        CIFSContext baseContext = new BaseContext(config);


        CIFSContext contextWithCred = baseContext.withCredentials(new NtlmPasswordAuthenticator(null, "", ""));
        SmbFile share = new SmbFile("smb://192.168.1.103/public", contextWithCred);
        boolean a = share.exists();

        CloseableIterator c = share.children();

        int b = 0;
    }

}
