package local.benchmark.spring;

public final class SupportModels {
    private SupportModels() {
    }

    public static class Account {
        private Long id;
        private String displayName;
        private String email;
        private boolean admin;
        private String role;
        private int creditLimit;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public int getCreditLimit() {
            return creditLimit;
        }

        public void setCreditLimit(int creditLimit) {
            this.creditLimit = creditLimit;
        }
    }

    public static class UserProfile {
        private String displayName;
        private String email;
        private SecuritySettings securitySettings = new SecuritySettings();

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public SecuritySettings getSecuritySettings() {
            return securitySettings;
        }

        public void setSecuritySettings(SecuritySettings securitySettings) {
            this.securitySettings = securitySettings;
        }
    }

    public static class SecuritySettings {
        private boolean mfaEnabled;
        private String recoveryEmail;
        private String privilegedRole;

        public boolean isMfaEnabled() {
            return mfaEnabled;
        }

        public void setMfaEnabled(boolean mfaEnabled) {
            this.mfaEnabled = mfaEnabled;
        }

        public String getRecoveryEmail() {
            return recoveryEmail;
        }

        public void setRecoveryEmail(String recoveryEmail) {
            this.recoveryEmail = recoveryEmail;
        }

        public String getPrivilegedRole() {
            return privilegedRole;
        }

        public void setPrivilegedRole(String privilegedRole) {
            this.privilegedRole = privilegedRole;
        }
    }

    public static class UpdateProfileRequest {
        private String displayName;
        private String email;

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class SafePayload {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
