import { FormGroup, ValidatorFn, ValidationErrors } from "@angular/forms";

export const pwdMatchValidator: ValidatorFn=(control: FormGroup): ValidationErrors | null => {
    console.log(control.errors);
    return control.get('password').value === control.get('confirmPassword').value
       ? null : {'mismatch': true};
  };