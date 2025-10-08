<script lang="ts">
	import type { PageData } from './$types';

	let { data }: { data: PageData } = $props();
	
	let tasks = $derived(data.apiResponse);
</script>

<div class="govuk-grid-row">
	<div class="govuk-grid-column-two-thirds">
		<h1 class="govuk-heading-xl">Available tasks</h1>

		{#if tasks.length === 0}
			<p class="govuk-body">No available tasks.</p>
		{:else}
			<p class="govuk-body">
				{tasks.length}
				{tasks.length === 1 ? 'task' : 'tasks'} available to review.
			</p>

			{#each tasks as task, index}
				<div class="govuk-!-margin-bottom-8">
					<h2 class="govuk-heading-l">
						{task.elementId.split('_').join(' ')} 
						<!-- <strong
							class="govuk-tag {getStatusTag(application.applicationStatus)
								.class} govuk-!-margin-left-2"
						>
							{getStatusTag(application.applicationStatus).text}
						</strong> -->
					</h2>

					<dl class="govuk-summary-list govuk-!-margin-bottom-4">
						<div class="govuk-summary-list__row">
							<dt class="govuk-summary-list__key">Task ID</dt>
							<dd class="govuk-summary-list__value">{task.id}</dd>
						</div>
						<!-- <div class="govuk-summary-list__row">
							<dt class="govuk-summary-list__key">Status</dt>
							<dd class="govuk-summary-list__value">
								{getStatusTag(application.applicationStatus).text}
							</dd>
						</div> -->
						<!-- {#if application.information}
							<div class="govuk-summary-list__row">
								<dt class="govuk-summary-list__key">Information</dt>
								<dd class="govuk-summary-list__value">{application.information}</dd>
							</div>
						{/if} -->
					</dl>

					<p class="govuk-body">
						<a href="/protected/reviewer/applications/{task.id}" class="govuk-link">View application details</a>
					</p>
				</div>

				{#if index !== tasks.length - 1}
					<hr class="govuk-section-break govuk-section-break--xl govuk-section-break--visible" />
				{/if}
			{/each}
		{/if}
	</div>
</div>