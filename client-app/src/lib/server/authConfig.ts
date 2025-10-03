import {
    KEYCLOAK_CLIENT_ID,
    KEYCLOAK_CLIENT_SECRET,
    KEYCLOAK_ISSUER,
    AUTH_SECRET
} from '$env/static/private';
import type { Provider } from '@auth/core/providers';
import Keycloak from '@auth/core/providers/keycloak';
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
    }
});
