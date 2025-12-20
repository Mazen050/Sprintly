import { Component } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  menuActive = false;
cartCount: any;

  toggleMenu(): void {
    this.menuActive = !this.menuActive;
  }
}