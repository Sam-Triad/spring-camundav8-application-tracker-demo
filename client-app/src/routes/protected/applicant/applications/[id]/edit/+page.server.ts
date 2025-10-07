import { redirect } from '@sveltejs/kit';
import type { Actions, PageServerLoad } from './$types';
import { getApplication, updateApplication } from '$lib/api/api';
import type { Application } from '$lib/api/types';

export const actions: Actions = {
	update: async ({ request, params, fetch, locals }) => {
		const session = await locals.auth();
		const formData = await request.formData();
		const information = formData.get('information') as string;

		await updateApplication(
			params.id,
			information,
			{ fetch, accessToken: session?.accessToken }
		);

		throw redirect(303, `/protected/applicant/applications/${params.id}`);
	}
};