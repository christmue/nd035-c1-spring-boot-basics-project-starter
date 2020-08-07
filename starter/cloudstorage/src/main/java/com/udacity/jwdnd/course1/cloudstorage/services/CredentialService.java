package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int addCredential(Credential credential) {
        String encodedKey = getRandomKey();
        credential.setKey(encodedKey);
        String encryptedPassword = this.encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setPassword(encryptedPassword);
        return this.credentialMapper.insert(credential);
    }

    public int editCredential(Credential credential) {
        String encodedKey = getRandomKey();
        credential.setKey(encodedKey);
        String encryptedPassword = this.encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setPassword(encryptedPassword);
        return this.credentialMapper.update(credential);
    }

    public List<Credential> getAllCredentials(Integer userId) {
        return this.credentialMapper.getAllCredentials(userId);
    }

    public int deleteCredential(Integer credentialId) {
        return this.credentialMapper.delete(credentialId);
    }

    public String getRandomKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        return encodedKey;
    }


}
