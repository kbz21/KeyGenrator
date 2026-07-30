/**
 * Composable for exporting keys in different formats
 */
export function useExport() {
  
  /**
   * Export key as plain text file
   */
  function exportAsText(key, filename) {
    console.log('📄 Exporting as TXT:', filename)
    const content = generateKeyContent(key)
    downloadFile(content, `${filename}.txt`, 'text/plain')
  }

  /**
   * Export key as JSON file
   */
  function exportAsJSON(key, filename) {
    console.log('📋 Exporting as JSON:', filename)
    const jsonData = JSON.stringify({
      id: key.id,
      algorithm: key.algorithm,
      keySize: key.keySize,
      key: key.generatedKey,
      alias: key.alias || '',
      createdAt: key.createdAt
    }, null, 2)
    downloadFile(jsonData, `${filename}.json`, 'application/json')
  }

  /**
   * Export key as PEM file
   */
  function exportAsPEM(key, filename) {
    console.log('🔐 Exporting as PEM:', filename)
    let pemContent = ''
    
    if (key.algorithm === 'AES') {
      pemContent = `-----BEGIN AES KEY-----\n${key.generatedKey}\n-----END AES KEY-----`
    } else if (key.algorithm === 'RSA') {
      pemContent = `-----BEGIN RSA PRIVATE KEY-----\n${key.generatedKey}\n-----END RSA PRIVATE KEY-----`
    } else {
      pemContent = `-----BEGIN KEY-----\n${key.generatedKey}\n-----END KEY-----`
    }
    
    downloadFile(pemContent, `${filename}.pem`, 'text/plain')
  }

  /**
   * Generate key content with metadata
   */
  function generateKeyContent(key) {
    return `========================================
🔑 Key Information
========================================
ID: ${key.id}
Algorithm: ${key.algorithm}
Key Size: ${key.keySize} bits
Alias: ${key.alias || 'N/A'}
Created: ${new Date(key.createdAt).toLocaleString()}
========================================
🔐 Key (Base64):
${key.generatedKey}
========================================
`
  }

  /**
   * Download file helper
   */
  function downloadFile(content, filename, mimeType) {
    try {
      console.log('📥 Downloading:', filename)
      
      // Create blob
      const blob = new Blob([content], { type: mimeType })
      
      // Create URL
      const url = URL.createObjectURL(blob)
      
      // Create download link
      const link = document.createElement('a')
      link.href = url
      link.download = filename
      
      // Append to body, click, remove
      document.body.appendChild(link)
      link.click()
      
      // Cleanup
      setTimeout(() => {
        document.body.removeChild(link)
        URL.revokeObjectURL(url)
        console.log('✅ Download complete:', filename)
      }, 100)
      
      return true
    } catch (error) {
      console.error('❌ Download failed:', error)
      return false
    }
  }

  /**
   * Export all keys as zip
   */
  async function exportAllAsZip(keys, zipName = 'keys-export') {
    try {
      console.log('📦 Exporting all keys as ZIP...')
      
      // Dynamic import for JSZip
      const JSZip = (await import('jszip')).default
      const zip = new JSZip()
      
      keys.forEach((key, index) => {
        const filename = `${key.algorithm}-${key.keySize}-${key.id || index + 1}`
        
        // Add TXT
        zip.file(`${filename}.txt`, generateKeyContent(key))
        
        // Add JSON
        zip.file(`${filename}.json`, JSON.stringify({
          id: key.id,
          algorithm: key.algorithm,
          keySize: key.keySize,
          key: key.generatedKey,
          alias: key.alias || '',
          createdAt: key.createdAt
        }, null, 2))
        
        // Add PEM
        let pemContent = ''
        if (key.algorithm === 'AES') {
          pemContent = `-----BEGIN AES KEY-----\n${key.generatedKey}\n-----END AES KEY-----`
        } else if (key.algorithm === 'RSA') {
          pemContent = `-----BEGIN RSA PRIVATE KEY-----\n${key.generatedKey}\n-----END RSA PRIVATE KEY-----`
        } else {
          pemContent = `-----BEGIN KEY-----\n${key.generatedKey}\n-----END KEY-----`
        }
        zip.file(`${filename}.pem`, pemContent)
      })
      
      const zipBlob = await zip.generateAsync({ type: 'blob' })
      downloadFile(zipBlob, `${zipName}.zip`, 'application/zip')
      console.log('✅ ZIP export complete!')
      return true
    } catch (error) {
      console.error('❌ ZIP export failed:', error)
      // Fallback: export as text
      return exportAllAsText(keys, zipName)
    }
  }

  /**
   * Fallback: Export all keys as a single text file
   */
  function exportAllAsText(keys, filename = 'keys-export') {
    let content = '========================================\n'
    content += '🔑 KEY EXPORT\n'
    content += `📅 Exported: ${new Date().toLocaleString()}\n`
    content += `📊 Total Keys: ${keys.length}\n`
    content += '========================================\n\n'
    
    keys.forEach((key, index) => {
      content += `--- Key ${index + 1} ---\n`
      content += generateKeyContent(key)
      content += '\n'
    })
    
    downloadFile(content, `${filename}.txt`, 'text/plain')
    return true
  }

  return {
    exportAsText,
    exportAsJSON,
    exportAsPEM,
    exportAllAsZip,
    exportAllAsText,
    generateKeyContent,
    downloadFile
  }
}
