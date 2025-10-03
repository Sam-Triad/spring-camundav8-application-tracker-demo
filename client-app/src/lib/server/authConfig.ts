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
            return session;
        }
    }
});