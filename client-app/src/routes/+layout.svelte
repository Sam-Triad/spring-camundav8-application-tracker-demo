<script lang="ts">
	import '../app.css';
	import 'govuk-frontend/dist/govuk/govuk-frontend.min.css';
	import { onMount } from 'svelte';
	import favicon from '$lib/assets/favicon.svg';

	let { children } = $props();

	onMount(async () => {
		const { initAll } = await import('govuk-frontend');
		initAll();
	});
</script>

<svelte:head>
	<link rel="icon" href={favicon} />
</svelte:head>

<header class="govuk-header" data-module="govuk-header">
	<div class="govuk-header__container govuk-width-container">
		<div class="govuk-header__content">
			<a href="/" class="govuk-header__link govuk-header__service-name">
				Application Tracker
			</a>
		</div>
	</div>
</header>

<div class="govuk-width-container">
	<main class="govuk-main-wrapper" id="main-content">
		{@render children?.()}
	</main>
</div>

<style>
	:global(body) {
		margin: 0;
	}
</style>