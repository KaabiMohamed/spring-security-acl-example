package com.med.security.access;

import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AclPermissionServiceImpl implements AclPermissionService {

    private final MutableAclService aclService;

    @Override
    @Transactional
    public void grantAccessToObject(Object object, String username, Permission permission) {
        // Create an< ObjectIdentity for the given object
        ObjectIdentityImpl objectIdentity = new ObjectIdentityImpl(object);

        // Get or create the ACL for the object
        MutableAcl acl = getOrCreateAcl(objectIdentity);

        // Create a SID (Security Identity) for the user
        Sid userSid = initAclPrincipalSid(username);

        // Grant the specified permission to the user
        acl.insertAce(acl.getEntries().size(), permission, userSid, true);

        // Update the ACL in the database
        aclService.updateAcl(acl);
    }

    @Override
    public void revokeAccessToObject(Object object, String user, boolean deleteChildren) {
        ObjectIdentityImpl objectIdentity = new ObjectIdentityImpl(object);
        aclService.deleteAcl(objectIdentity, deleteChildren);
    }


    private MutableAcl getOrCreateAcl(ObjectIdentityImpl objectIdentity) {
        MutableAcl acl;
        try {
            acl = (MutableAcl) aclService.readAclById(objectIdentity);
        } catch (org.springframework.security.acls.model.NotFoundException e) {
            acl = aclService.createAcl(objectIdentity);
        }
        return acl;
    }

    private Sid initAclPrincipalSid(String user) {
        return new org.springframework.security.acls.domain.PrincipalSid(user);
    }


}
