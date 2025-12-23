import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.css'
})
export class AuthComponent implements OnInit {
  isLogin = true;
  errorMessage: string | null = null;

  formData = {
    fullName: '',
    email: '',
    password: ''
  };

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.route.url.subscribe(segments => {
      if (segments.length > 0) {
        const path = segments[0].path;
        this.isLogin = (path === 'login');
      }
    });
  }

  switchMode(mode: 'login' | 'signup'): void {
    this.isLogin = (mode === 'login');
    this.errorMessage = null;
    this.router.navigate([mode === 'login' ? '/login' : '/signup']);
  }

  onSubmit(form: NgForm): void {
    if (form.valid) {
      this.errorMessage = null;

      if (this.isLogin) {
        const loginData = {
          email: this.formData.email,
          password: this.formData.password
        };

        this.authService.login(loginData).subscribe({
          next: (response: any) => {
            console.log('Login Success!', response);
            if (response.token) {
              this.authService.saveToken(response.token);
            }
            this.router.navigate(['/']);
          },
          error: (err) => {
            console.error('Login Failed', err);
            this.errorMessage = "Login failed. Please check your email and password.";
          }
        });

      } else {
        const registerData = {
          name: this.formData.fullName,
          email: this.formData.email,
          password: this.formData.password
        };

        console.log('Sending to backend:', registerData);

        this.authService.register(registerData).subscribe({
          next: (response) => {
            console.log('Registration Success!', response);
            alert('Account created! Please log in.');
            this.switchMode('login');
          },
          error: (err) => {
            console.error('Registration Failed', err);
            this.errorMessage = err.error?.message || "Registration failed. This email already in use.";
          }
        });
      }
    }
  }
}