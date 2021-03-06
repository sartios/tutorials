package com.ssavr.tutorials.sportify.client;

import com.ssavr.tutorials.sportify.dto.TokenDto;
import com.ssavr.tutorials.sportify.utils.JSONUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class TokenClient {

	public static TokenDto getScopedAccessToken() {
		TokenDto tokenDto = new TokenDto(
				"BQCmGP88eKkpvG73pyfbMjqdGFnNXJB-biXPdf6OcQsPKDRFBe-Xm60hVabpQBFlHVtVoiTti1ExmG9_1DUvP0e1Ft_qUPZRu7dtqP2PQNTkJHjkf-IT8dMxGOb2xM9GcVskbddMOCqnUnceLng9FkvFucLFbhbF4Fg", "Bearer");
		return tokenDto;
	}

	public static TokenDto getAccessToken() {
		URI uri;
		try {
			uri = new URIBuilder().setScheme("https")
					.setHost("accounts.spotify.com").setPath("/api/token")
					.addParameter("scope", "user-follow-modify").build();
		} catch (URISyntaxException e) {
			_log.error(e.getMessage());
			return null;
		}

		HttpPost httpPost = new HttpPost(uri);

		String clientId = "1d0fba0aaf4140e19a7d8d62f6e69903";
		String clientSecret = "d1f96f037cee4d5fa8d53129edc53c08";
		String auth = clientId + ":" + clientSecret;

		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());
		String authHeader = "Basic " + new String(encodedAuth);
		httpPost.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("grant_type",
				"client_credentials"));

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e) {
			_log.error(e.getMessage());
		}

		log(httpPost);

		TokenDto tokenDto = executeRequest(httpPost, getResponseHandler());
		return tokenDto;
	}

	private static ResponseHandler<TokenDto> getResponseHandler() {
		ResponseHandler<TokenDto> responseHandler = new ResponseHandler<TokenDto>() {
			public TokenDto handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
				TokenDto tokenDto = null;
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == HttpStatus.SC_OK) {
					tokenDto = new TokenDto(JSONUtil.getJsonObject(EntityUtils
							.toString(response.getEntity())));
				} else {
					_log.error("Request failed, status code: " + statusCode);
				}
				return tokenDto;
			}
		};

		return responseHandler;
	}

	private static TokenDto executeRequest(HttpUriRequest httpPost,
			ResponseHandler<TokenDto> responseHandler) {
		TokenDto tokenDto = null;
		HttpClient client = HttpClientBuilder.create().build();
		try {
			tokenDto = client.execute(httpPost, responseHandler);
		} catch (ClientProtocolException e) {
			_log.error(e.getMessage());
		} catch (IOException e) {
			_log.error(e.getMessage());
		}
		return tokenDto;
	}

	private static void log(HttpPost httpPost) {
		System.out.println(httpPost.getMethod());
		System.out.println(httpPost.getURI());
		Header[] headers = httpPost.getAllHeaders();
		for (Header header : headers) {
			System.out.println(header.getName() + " => " + header.getValue());
		}
		try {
			if (_log.isDebugEnabled()) {
				_log.debug(EntityUtils.toString(httpPost.getEntity()));
			}
		} catch (ParseException e) {
			_log.error(e.getMessage());
		} catch (IOException e) {
			_log.error(e.getMessage());
		}
	}

	final static Logger _log = Logger.getLogger(TokenClient.class);
}
