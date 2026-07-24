<template>
  <div class="row justify-content-center">
    <div class="col-lg-8">
      <div class="card shadow">
        <div class="card-body p-4">
          <h1 class="card-title text-center mb-4">
            <i class="bi bi-info-circle-fill text-primary me-2"></i>
            About
          </h1>

          <div class="mb-4">
            <h5><i class="bi bi-key text-primary me-2"></i> Key Generator</h5>
            <p>A secure key generation application built with modern web technologies.</p>
          </div>

          <hr />

          <div class="mb-4">
            <h5><i class="bi bi-tools text-primary me-2"></i> Technologies</h5>
            <div class="d-flex flex-wrap gap-2">
              <span class="badge bg-primary">Vue.js 3</span>
              <span class="badge bg-success">Spring Boot</span>
              <span class="badge bg-info">MariaDB</span>
              <span class="badge bg-warning text-dark">Pinia</span>
              <span class="badge bg-danger">Axios</span>
              <span class="badge bg-secondary">Bootstrap</span>
            </div>
          </div>

          <hr />

          <div class="mb-4">
            <h5><i class="bi bi-shield-check text-primary me-2"></i> Supported Algorithms</h5>
            <ul class="list-group">
              <li class="list-group-item d-flex justify-content-between align-items-center">
                <span><strong>AES</strong> - Advanced Encryption Standard</span>
                <span class="badge bg-primary rounded-pill">128/192/256 bits</span>
              </li>
              <li class="list-group-item d-flex justify-content-between align-items-center">
                <span><strong>RSA</strong> - Rivest-Shamir-Adleman</span>
                <span class="badge bg-success rounded-pill">1024/2048/4096 bits</span>
              </li>
            </ul>
          </div>

          <hr />

          <div class="mb-4">
            <h5><i class="bi bi-star text-primary me-2"></i> Features</h5>
            <ul class="list-unstyled">
              <li><i class="bi bi-check-lg text-success me-2"></i> Secure key generation</li>
              <li><i class="bi bi-check-lg text-success me-2"></i> Copy to clipboard</li>
              <li><i class="bi bi-check-lg text-success me-2"></i> Key history</li>
              <li><i class="bi bi-check-lg text-success me-2"></i> Filter by algorithm</li>
              <li><i class="bi bi-check-lg text-success me-2"></i> Delete keys</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
