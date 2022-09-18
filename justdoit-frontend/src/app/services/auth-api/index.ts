import { Inject, Injectable, Optional } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import {
  AuthenticationSuccess,
  ForgotPasswordRequest,
  RefreshTokenSuccess,
  RegistrationRequest,
  ResetPasswordRequest,
  UserAuthenticationRequest,
  UserTransport
} from './models';

/**
 * Created with angular-swagger-client-generator.
 */
@Injectable({
  providedIn: 'root',
})
export class AuthApiClientService {

  private domain = 'http://192.168.0.14:9191';

  constructor(private http: HttpClient, @Optional() @Inject('domain') domain: string ) {
    if (domain) {
      this.domain = domain;
    }
  }

  /**
   * Method loginUsingPOST
   * @param request request
   * @return Full HTTP response as Observable
   */
  public loginUsingPOST(request: UserAuthenticationRequest): Observable<AuthenticationSuccess> {
    let uri = `/auth/login`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    return this.sendRequest<AuthenticationSuccess>('post', uri, headers, params, JSON.stringify(request));
  }

  /**
   * Method refreshTokenUsingGET
   * @param token token
   * @return Full HTTP response as Observable
   */
  public refreshTokenUsingGET(token: string): Observable<RefreshTokenSuccess> {
    let uri = `/auth/refresh-token/${token}`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    return this.sendRequest<RefreshTokenSuccess>('get', uri, headers, params, null);
  }

  /**
   * Method forgotPasswordUsingPOST
   * @param forgotPasswordRequest forgotPasswordRequest
   * @return Full HTTP response as Observable
   */
  public forgotPasswordUsingPOST(forgotPasswordRequest: ForgotPasswordRequest): Observable<any> {
    let uri = `/auth/users/forgot-password`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    return this.sendRequest<any>('post', uri, headers, params, JSON.stringify(forgotPasswordRequest));
  }

  /**
   * Method validateResetTokenUsingGET
   * @param token token
   * @return Full HTTP response as Observable
   */
  public validateResetTokenUsingGET(token: string): Observable<any> {
    let uri = `/auth/users/reset-password/${token}`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    return this.sendRequest<any>('get', uri, headers, params, null);
  }

  /**
   * Method resetUserPasswordUsingPOST
   * @param resetPasswordRequest resetPasswordRequest
   * @param token token
   * @return Full HTTP response as Observable
   */
  public resetUserPasswordUsingPOST(resetPasswordRequest: ResetPasswordRequest, token: string): Observable<any> {
    let uri = `/auth/users/reset-password/${token}`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    return this.sendRequest<any>('post', uri, headers, params, JSON.stringify(resetPasswordRequest));
  }

  /**
   * Method registerAccountUsingPOST
   * @param registrationRequest registrationRequest
   * @return Full HTTP response as Observable
   */
  public registerAccountUsingPOST(registrationRequest: RegistrationRequest): Observable<UserTransport> {
    let uri = `/auth/users/sign-up`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    return this.sendRequest<UserTransport>('post', uri, headers, params, JSON.stringify(registrationRequest));
  }

  /**
   * Method userConfirmationUsingGET
   * @param token token
   * @return Full HTTP response as Observable
   */
  public userConfirmationUsingGET(token: string): Observable<any> {
    let uri = `/auth/users/user-confirmation/${token}`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    return this.sendRequest<any>('get', uri, headers, params, null);
  }

  private sendRequest<T>(method: string, uri: string, headers: HttpHeaders, params: HttpParams, body: any): Observable<T> {
    if (method === 'get') {
      return this.http.get<T>(this.domain + uri, { headers: headers.set('Accept', 'application/json'), params: params });
    } else if (method === 'put') {
      return this.http.put<T>(this.domain + uri, body, { headers: headers.set('Content-Type', 'application/json'), params: params });
    } else if (method === 'post') {
      return this.http.post<T>(this.domain + uri, body, { headers: headers.set('Content-Type', 'application/json'), params: params });
    } else if (method === 'delete') {
      return this.http.delete<T>(this.domain + uri, { headers: headers, params: params });
    } else {
      console.error('Unsupported request: ' + method);
      return Observable.throw('Unsupported request: ' + method);
    }
  }
}
