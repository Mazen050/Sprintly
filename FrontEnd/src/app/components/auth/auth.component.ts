import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.css'
})
export class AuthComponent implements OnInit {
  isLogin = true;

  formData = {
    fullName: '',
    email: '',
    password: ''
  };

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.url.subscribe(segments => {
      const path = segments[0].path;
      this.isLogin = (path === 'login');
    });
  }

  switchMode(mode: 'login' | 'signup'): void {
    this.isLogin = (mode === 'login');
  }

  onSubmit(form: NgForm): void {
    if (form.valid) {
      console.log('Form is Valid!', this.formData);

    } else {
      console.log('Form is Invalid');
      Object.keys(form.controls).forEach(field => {
        const control = form.control.get(field);
        control?.markAsTouched({ onlySelf: true });
      });
    }
  }
}