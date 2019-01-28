import { Router, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivate } from "@angular/router";
import { AuthenticationService } from "../_services/authentication/authentication.service";
import { Injectable } from "@angular/core";

@Injectable({providedIn: 'root'})
export class RoleGuard implements CanActivate{
    constructor(
        private router: Router,
        private authenticationService: AuthenticationService
    ){}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){
       
        const currentUser = this.authenticationService.currentUserValue;

        if(currentUser){
            if(route.data.roles && route.data.roles.indexOf(currentUser.role) === -1){
                this.router.navigate(['/']);
                return false;
            }
        }

        return true;
    }

}