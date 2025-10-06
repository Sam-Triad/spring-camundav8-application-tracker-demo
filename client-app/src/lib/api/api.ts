import { JSON_HEADERS, JSON_POST_HEADERS } from './constants';
import { ApiError, type ProblemDetails } from './errors';
import { buildUrl, type RequestConfig, HttpMethod, type RequestProperties } from './fetchHelpers';
import type {
	Application,
	Page,
	SortDirection
} from './types';

export async function makeRequest<T>(
	config: RequestConfig,
	properties: RequestProperties,
	payload?: any
): Promise<T> {
	console.log("makeRequest");
	const url = buildUrl(properties.url, properties.params);
	const $fetch = config.fetch;

	const headers = payload ? { ...JSON_POST_HEADERS } : { ...JSON_HEADERS };
	if (config.accessToken) {
		headers['Authorization'] = `Bearer ${config.accessToken}`;
	}

	const res = await $fetch(url.toString(), {
		method: properties.httpMethod,
		signal: config.signal,
		body: JSON.stringify(payload),
		headers
	});

	if (!res.ok) {
		console.error(`API request failed: ${res.status} ${res.statusText}`);
		
		let body: ProblemDetails;
		try {
			body = (await res.json()) as ProblemDetails;
		} catch (error) {
			body = {
				type: 'about:blank',
				instance: '',
				title: res.statusText,
				status: res.status,
				detail: 'No additional details provided'
			};
		}
		
		throw new ApiError(body);
	}

	const data = (await res.json()) as T;
	if (!data) throw new Error('Empty response from API');

	return data;
}

export async function getApplications(
	params: {
		page?: number;
		size?: number;
		sortDir?: SortDirection;
	},
	config: RequestConfig
): Promise<Page<Application>> {
	return makeRequest<Page<Application>>(config, {
		url: '/applications',
		httpMethod: HttpMethod.GET,
		params: params
	});
}

export async function getApplication(
	applicationId: string,
	config: RequestConfig
): Promise<Application> {
	return makeRequest<Application>(config, {
		url: `/applications/${applicationId}`,
		httpMethod: HttpMethod.GET
	});
}
