import {
    KEYCLOAK_CLIENT_ID,
    KEYCLOAK_CLIENT_SECRET,
    KEYCLOAK_ISSUER,
    AUTH_SECRET
} from '$env/static/private';
import type { Provider } from '@auth/core/providers';
import Keycloak, { type KeycloakProfile } from '@auth/core/providers/keycloak';
import { SvelteKitAuth } from "@auth/sveltekit";

const keycloak: Provider = Keycloak({
    clientId: KEYCLOAK_CLIENT_ID,
    clientSecret: KEYCLOAK_CLIENT_SECRET,
    issuer: KEYCLOAK_ISSUER
});

export const { handle, signIn, signOut } = SvelteKitAuth({
    providers: [keycloak],
    secret: AUTH_SECRET,
    trustHost: true,
    pages: {
        signIn: '/signin'
    },
    callbacks: {
        async jwt({ token, account, profile }) {
            // Persist groups/roles to the token right after signin
            if (account && profile) {
                const keycloakProfile = profile as KeycloakProfile;
                
                // Store access token from account
                token.accessToken = account.access_token;
                token.refreshToken = account.refresh_token;
                token.expiresAt = account.expires_at;
                
                // Use groups from Keycloak
                token.roles = keycloakProfile.groups || [];
            }
            return token;
        },
        async session({ session, token }) {
            // Send roles to the client in the session
            if (session.user) {
                session.user.roles = (token.roles as string[]) || [];
            }
            // Add access token to session
            session.accessToken = token.accessToken as string;
            
            return session;
        }
    }
});