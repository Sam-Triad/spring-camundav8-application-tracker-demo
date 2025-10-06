<script lang="ts">
	import { enhance } from '$app/forms';
	import type { Application } from '$lib/api/types';
	import type { PageData } from './$types';

	let { data }: { data: PageData } = $props();

	let application: Application = {
		applicantUsername: 'Bob',
		applicationStatus: 'IN_REVIEW',
		id: '9b0efb90-df89-4b8b-86a9-d1e080f45b95',
		information:
			'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.'
	};
</script>

<div class="govuk-grid-row">
	<div class="govuk-grid-column-two-thirds">
		<a href="/protected/applicant/applications/{application.id}" class="govuk-back-link">Back to application</a>

		<h1 class="govuk-heading-xl govuk-!-margin-top-6">Edit Application Information</h1>

		<form method="POST" action="?/update" use:enhance>
			<div class="govuk-form-group">
				<label class="govuk-label govuk-label--m" for="information">
					Application information
				</label>
				<div id="information-hint" class="govuk-hint">
					Provide any additional information about your application.
				</div>
				<textarea
					class="govuk-textarea"
					id="information"
					name="information"
					rows="8"
					aria-describedby="information-hint"
					value={application.information || ''}
				></textarea>
			</div>

			<div class="govuk-button-group">
				<button type="submit" class="govuk-button" data-module="govuk-button">
					Save and continue
				</button>
				<a class="govuk-link" href="/protected/applicant/applications/{application.id}">Cancel</a>
			</div>
		</form>
	</div>
</div>
