import axiosInstance from './axios'

export const keyApi = {
  generateKey(algorithm, keySize) {
    return axiosInstance.post('/keys/generate', {
      algorithm: algorithm.toUpperCase(),
      keySize: parseInt(keySize)
    })
  },

  getAllKeys() {
    return axiosInstance.get('/keys')
  },

  getKeysByAlgorithm(algorithm) {
    return axiosInstance.get(`/keys/algorithm/${algorithm.toUpperCase()}`)
  },

  deleteKey(id) {
    return axiosInstance.delete(`/keys/${id}`)
  }
}
