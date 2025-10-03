<script lang="ts">
	import { signIn } from '@auth/sveltekit/client';
</script>

<button class="w-full" on:click={() => signIn('keycloak')}>Sign In with Keycloak</button>
