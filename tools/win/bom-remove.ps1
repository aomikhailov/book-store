# /tools/win/bom-remove.ps1
# --------------------------------------------------------------------
# Скрипт рекурсивно ищет в папке проекта файлы с нужными расширениями,
# проверяет наличие BOM и при обнаружении удаляет его из файла,
# выводит список и общее количество таких файлов.
# --------------------------------------------------------------------
# Версия:    1.2
# Создано:   2025-04-24
# Обновлено: 2025-04-24
# Автор:     Александр Михайлов
# --------------------------------------------------------------------

# Переход к папке проекта
Push-Location -Path (Join-Path $PSScriptRoot "..\..")
Write-Host "Рекурсивный поиск и удаление BOM файлов в папке: $PWD"

# Расширения файлов для обработки
$exts = @(".java", ".xml", ".properties", ".env", ".md", ".txt", ".gitignore", ".gitattributes", ".editorconfig", ".yml", ".yaml", ".json")

# Счётчик обработанных файлов
$count = 0

# Рекурсивный обход файлов
Get-ChildItem -Recurse -File | ForEach-Object {
    $file = $_.FullName
    $ext = $_.Extension.ToLower()

    if ($exts -contains $ext) {
        try {
            # Проверяем доступ к файлу
            if (!(Test-Path $file)) {
                Write-Warning "Файл не найден: $file"
                return
            }

            # Проверяем первые 3 байта файла на наличие BOM
            $stream = [System.IO.File]::OpenRead($file)
            $bytes = New-Object byte[] 3
            $stream.Read($bytes, 0, 3) | Out-Null
            $stream.Close()

            if (($bytes[0] -eq 0xEF) -and ($bytes[1] -eq 0xBB) -and ($bytes[2] -eq 0xBF)) {
                try {
                    # Читаем весь файл в байтовом формате
                    $content = [System.IO.File]::ReadAllBytes($file)

                    # Записываем файл, начиная с 4-го байта (удаляя BOM)
                    [System.IO.File]::WriteAllBytes($file, $content[3..($content.Length - 1)])

                    $count++
                    Write-Host "Удалён BOM: $file"
                } catch {
                    Write-Warning "Ошибка при попытке записи файла: $file"
                    Write-Warning $_.Exception.Message
                }
            }
        } catch {
            Write-Warning "Не удалось обработать файл: $file"
            Write-Warning $_.Exception.Message
        }
    }
}

# Итоговый вывод
Write-Host "Удаление завершено. Удалено BOM из файлов: $count"

# Возврат к исходной папке
Pop-Location