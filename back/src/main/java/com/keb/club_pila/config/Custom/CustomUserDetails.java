package com.keb.club_pila.config.Custom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class CustomUserDetails implements UserDetails {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private static final Log logger = LogFactory.getLog(User.class);

    private String password;

    private   String username;

    private   Collection<? extends GrantedAuthority> authorities;

    private   boolean accountNonExpired;

    private   boolean accountNonLocked;

    private   boolean credentialsNonExpired;

    private   boolean enabled;

    public CustomUserDetails(String password, String username, Collection<? extends GrantedAuthority> authorities, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        this.password = password;
        this.username = username;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    public CustomUserDetails( String username, String password, Collection<? extends GrantedAuthority> authorities){
        this(password, username, authorities, true, true, true, true);
    }
    public CustomUserDetails(String password, Set<GrantedAuthority> authorities, String username){
        this(password, username, authorities,true, true, true, true);
    }


    //    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
//       this(username, password, true, true, true, true,"false", authorities);
//
//    }
//    public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
//                boolean credentialsNonExpired, boolean accountNonLocked, String certified,
//                Collection<? extends GrantedAuthority> authorities) {
//        Assert.isTrue(username != null && !"".equals(username) && password != null,
//                "Cannot pass null or empty values to constructor");
//        this.username = username;
//        this.password = password;
//        this.enabled = enabled;
//        this.accountNonExpired = accountNonExpired;
//        this.credentialsNonExpired = credentialsNonExpired;
//        this.accountNonLocked = accountNonLocked;
//        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
//        this.certified=certified;
//    }
//    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
//        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
//        // Ensure array iteration order is predictable (as per
//        // UserDetails.getAuthorities() contract and SEC-717)
//        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(new User.AuthorityComparator());
//        for (GrantedAuthority grantedAuthority : authorities) {
//            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
//            sortedAuthorities.add(grantedAuthority);
//        }
//        return sortedAuthorities;
//    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" [");
        sb.append("Username=").append(this.username).append(", ");
        sb.append("Password=[PROTECTED], ");
        sb.append("Enabled=").append(this.enabled).append(", ");
        sb.append("AccountNonExpired=").append(this.accountNonExpired).append(", ");
        sb.append("credentialsNonExpired=").append(this.credentialsNonExpired).append(", ");
        sb.append("AccountNonLocked=").append(this.accountNonLocked).append(", ");
        sb.append("Granted Authorities=").append(this.authorities).append("]");
        return sb.toString();
    }
}
