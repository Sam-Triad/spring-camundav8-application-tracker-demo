import { getApplication } from "$lib/api/api";
import type { Application } from "$lib/api/types";
import type { PageServerLoad } from "./$types";

export const load: PageServerLoad = async ({ fetch, locals, params }) => {
    const session = await locals.auth();

    const apiResponse: Application = await getApplication(
        params.id,
        { fetch, accessToken: session?.accessToken }
    );
    return {
        title: 'Application',
        apiResponse: apiResponse
    }
}
