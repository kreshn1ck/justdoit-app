import { Inject, Injectable, Optional } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import {
  TodoTransport,
  UserRelationshipTransport,
  UserTransport
} from './models';

/**
 * Created with angular-swagger-client-generator.
 */
@Injectable({
  providedIn: 'root',
})
export class ApiClientService {

  private domain = 'http://192.168.0.14:9191';

  constructor(private http: HttpClient, @Optional() @Inject('domain') domain: string ) {
    if (domain) {
      this.domain = domain;
    }
  }

  /**
   * Method getAllPeopleUsingGET
   * @param email email
   * @return Full HTTP response as Observable
   */
  public getAllPeopleUsingGET(): Observable<UserRelationshipTransport[]> {
    let uri = `/backend/api/relationship/all`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    return this.sendRequest<UserRelationshipTransport[]>('get', uri, headers, params, null);
  }

  /**
   * Method getAllFriendsUsingGET
   * @param userId userId
   * @return Full HTTP response as Observable
   */
  public getAllFriendsUsingGET(userId?: string): Observable<UserRelationshipTransport[]> {
    let uri = `/backend/api/relationship/friends`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    if (userId !== undefined && userId !== null) {
      headers = headers.set('userId', userId + '');
    }
    return this.sendRequest<UserRelationshipTransport[]>('get', uri, headers, params, null);
  }

  /**
   * Method geAllPendingRequestsUsingGET
   * @param userId userId
   * @return Full HTTP response as Observable
   */
  public geAllPendingRequestsUsingGET(userId?: string): Observable<UserRelationshipTransport[]> {
    let uri = `/backend/api/relationship/pending`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    if (userId !== undefined && userId !== null) {
      headers = headers.set('userId', userId + '');
    }
    return this.sendRequest<UserRelationshipTransport[]>('get', uri, headers, params, null);
  }

  /**
   * Method getAllRequestedRequestsUsingGET
   * @param userId userId
   * @return Full HTTP response as Observable
   */
  public getAllRequestedRequestsUsingGET(userId?: string): Observable<UserRelationshipTransport[]> {
    let uri = `/backend/api/relationship/requested`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    if (userId !== undefined && userId !== null) {
      headers = headers.set('userId', userId + '');
    }
    return this.sendRequest<UserRelationshipTransport[]>('get', uri, headers, params, null);
  }

  /**
   * Method getUsingGET_1
   * @param email email
   * @param id id
   * @param userId userId
   * @return Full HTTP response as Observable
   */
  public getUsingGET_1(id: number, userId?: string): Observable<UserRelationshipTransport> {
    let uri = `/backend/api/relationship/${id}`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    if (userId !== undefined && userId !== null) {
      headers = headers.set('userId', userId + '');
    }
    return this.sendRequest<UserRelationshipTransport>('get', uri, headers, params, null);
  }

  /**
   * Method createUsingPOST_1
   * @param email email
   * @param id id
   * @return Full HTTP response as Observable
   */
  public createUsingPOST_1(id: number): Observable<UserRelationshipTransport> {
    let uri = `/backend/api/relationship/${id}`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    return this.sendRequest<UserRelationshipTransport>('post', uri, headers, params, null);
  }

  /**
   * Method deleteUsingDELETE_1
   * @param email email
   * @param id id
   * @return Full HTTP response as Observable
   */
  public deleteUsingDELETE_1(id: number): Observable<any> {
    let uri = `/backend/api/relationship/${id}`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    return this.sendRequest<any>('delete', uri, headers, params, null);
  }

  /**
   * Method acceptUsingPUT
   * @param email email
   * @param id id
   * @param userId userId
   * @return Full HTTP response as Observable
   */
  public acceptUsingPUT(id: number, userId?: string): Observable<UserRelationshipTransport> {
    let uri = `/backend/api/relationship/${id}/accept`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    return this.sendRequest<UserRelationshipTransport>('put', uri, headers, params, null);
  }

  /**
   * Method cancelUsingDELETE
   * @param email email
   * @param id id
   * @return Full HTTP response as Observable
   */
  public cancelUsingDELETE(id: number): Observable<any> {
    let uri = `/backend/api/relationship/${id}/cancel`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    return this.sendRequest<any>('delete', uri, headers, params, null);
  }

  /**
   * Method rejectUsingPUT
   * @param email email
   * @param id id
   * @param userId userId
   * @return Full HTTP response as Observable
   */
  public rejectUsingPUT(id: number, userId?: string): Observable<UserRelationshipTransport> {
    let uri = `/backend/api/relationship/${id}/reject`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    return this.sendRequest<UserRelationshipTransport>('put', uri, headers, params, null);
  }

  /**
   * Method validateResetTokenUsingGET
   * @param userId userId
   * @return Full HTTP response as Observable
   */
  public validateResetTokenUsingGET(userId?: string): Observable<any> {
    let uri = `/backend/api/test`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    if (userId !== undefined && userId !== null) {
      headers = headers.set('userId', userId + '');
    }
    return this.sendRequest<any>('get', uri, headers, params, null);
  }

  /**
   * Method createUsingPOST
   * @param email email
   * @param todoTransport todoTransport
   * @return Full HTTP response as Observable
   */
  public createUsingPOST(todoTransport: TodoTransport): Observable<TodoTransport> {
    let uri = `/backend/api/todos`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    return this.sendRequest<TodoTransport>('post', uri, headers, params, JSON.stringify(todoTransport));
  }

  /**
   * Method getAssignedTodosUsingGET
   * @param email email
   * @param oldestFirst oldestFirst
   * @return Full HTTP response as Observable
   */
  public getAssignedTodosUsingGET(oldestFirst: boolean): Observable<TodoTransport[]> {
    let uri = `/backend/api/todos/assigned`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    return this.sendRequest<TodoTransport[]>('get', uri, headers, params, null);
  }

  /**
   * Method getCreatedTodosUsingGET
   * @param email email
   * @param oldestFirst oldestFirst
   * @return Full HTTP response as Observable
   */
  public getCreatedTodosUsingGET(oldestFirst: boolean): Observable<TodoTransport[]> {
    let uri = `/backend/api/todos/created`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    if (oldestFirst !== undefined && oldestFirst !== null) {
      params = params.set('oldestFirst', oldestFirst + '');
    }
    return this.sendRequest<TodoTransport[]>('get', uri, headers, params, null);
  }

  /**
   * Method getUsingGET
   * @param todoId todoId
   * @param userId userId
   * @return Full HTTP response as Observable
   */
  public getUsingGET(todoId: number, userId?: string): Observable<TodoTransport> {
    let uri = `/backend/api/todos/${todoId}`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    if (userId !== undefined && userId !== null) {
      headers = headers.set('userId', userId + '');
    }
    return this.sendRequest<TodoTransport>('get', uri, headers, params, null);
  }

  /**
   * Method updateUsingPUT
   * @param todoId todoId
   * @param todoTransport todoTransport
   * @param userId userId
   * @return Full HTTP response as Observable
   */
  public updateUsingPUT(todoId: number, todoTransport: TodoTransport, userId?: string): Observable<TodoTransport> {
    let uri = `/backend/api/todos/${todoId}`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    if (userId !== undefined && userId !== null) {
      headers = headers.set('userId', userId + '');
    }
    return this.sendRequest<TodoTransport>('put', uri, headers, params, JSON.stringify(todoTransport));
  }

  /**
   * Method deleteUsingDELETE
   * @param todoId todoId
   * @param userId userId
   * @return Full HTTP response as Observable
   */
  public deleteUsingDELETE(todoId: number, userId?: string): Observable<any> {
    let uri = `/backend/api/todos/${todoId}`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    if (userId !== undefined && userId !== null) {
      headers = headers.set('userId', userId + '');
    }
    return this.sendRequest<any>('delete', uri, headers, params, null);
  }

  /**
   * Method markFinishedUsingPUT
   * @param todoId todoId
   * @param userId userId
   * @return Full HTTP response as Observable
   */
  public markFinishedUsingPUT(todoId: number, userId?: string): Observable<TodoTransport> {
    let uri = `/backend/api/todos/${todoId}/finished`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    if (userId !== undefined && userId !== null) {
      headers = headers.set('userId', userId + '');
    }
    return this.sendRequest<TodoTransport>('put', uri, headers, params, null);
  }

  /**
   * Method markNotFinishedUsingPUT
   * @param todoId todoId
   * @param userId userId
   * @return Full HTTP response as Observable
   */
  public markNotFinishedUsingPUT(todoId: number, userId?: string): Observable<TodoTransport> {
    let uri = `/backend/api/todos/${todoId}/not-finished`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    if (userId !== undefined && userId !== null) {
      headers = headers.set('userId', userId + '');
    }
    return this.sendRequest<TodoTransport>('put', uri, headers, params, null);
  }

  /**
   * Method createUserUsingPOST
   * @param email email
   * @param id id
   * @param username username
   * @return Full HTTP response as Observable
   */
  public createUserUsingPOST(id: number, username: string): Observable<UserTransport> {
    let uri = `/backend/users/create`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    if (id !== undefined && id !== null) {
      params = params.set('id', id + '');
    }
    if (username !== undefined && username !== null) {
      params = params.set('username', username + '');
    }
    return this.sendRequest<UserTransport>('post', uri, headers, params, null);
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
