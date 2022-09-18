import { Injectable } from '@angular/core';
import {AuthApiClientService} from "../auth-api";
import { Observable } from 'rxjs';
import {RefreshTokenSuccess} from "../auth-api/models";

@Injectable({
  providedIn: 'root'
})
export class RefreshTokenService {

  constructor(private authApiClientService: AuthApiClientService) { }

  public refreshToken(token: string): Observable<RefreshTokenSuccess> {
    return this.authApiClientService.refreshTokenUsingGET(token);
  }
}
