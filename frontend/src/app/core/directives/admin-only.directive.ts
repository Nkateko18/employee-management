import { Directive, effect, inject, TemplateRef, ViewContainerRef } from '@angular/core';
import { RoleService } from '../services/role.service';

@Directive({
  selector: '[appAdminOnly]',
  standalone: true,
})
export class AdminOnlyDirective {
  private templateRef = inject(TemplateRef<unknown>);
  private viewContainer = inject(ViewContainerRef);
  private roleService = inject(RoleService);

  constructor() {
    effect(() => {
      this.viewContainer.clear();
      if (this.roleService.isAdmin()) {
        this.viewContainer.createEmbeddedView(this.templateRef);
      }
    });
  }
}
