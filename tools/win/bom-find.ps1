# /tools/win/bom-find.ps1
# --------------------------------------------------------------------
# Скрипт рекурсивно ищет в папке проекта файлы с нужными расширениями,
# проверяет наличие BOM и при обнаружении выводит список и общее
# количество таких файлов.
# --------------------------------------------------------------------
# Версия:    1.0
# Создано:   2025-04-24
# Обновлено: 2025-04-24
# Автор:     Александр Михайлов
# --------------------------------------------------------------------

# Переход к папке проекта
Push-Location -Path (Join-Path $PSScriptRoot "..\..")
Write-Host "Рекурсивный поиск BOM файлов в папке: $PWD"

# Расширения файлов для проверки на BOM
$exts = @(".java", ".xml", ".properties", ".env", ".md", ".txt", ".gitignore", ".gitattributes", ".editorconfig", ".yml", ".yaml", ".json")

# Счётчик для подсчёта файлов с BOM
$count = 0

# Рекурсивный обход файлов
Get-ChildItem -Recurse -File | ForEach-Object {
    $file = $_.FullName
    $ext = $_.Extension.ToLower()

    if ($exts -contains $ext) {
        try {
            # Проверяем первые 3 байта файла на наличие BOM
            $stream = [System.IO.File]::OpenRead($file)
            $bytes = New-Object byte[] 3
            $stream.Read($bytes, 0, 3) | Out-Null
            $stream.Close()

            if (($bytes[0] -eq 0xEF) -and ($bytes[1] -eq 0xBB) -and ($bytes[2] -eq 0xBF)) {
                $count++
                Write-Host "Найден BOM: $file"
            }
        } catch {
            Write-Warning "Не удалось обработать файл: $file"
        }
    }
}

# Вывод итогов
Write-Host "Поиск завершён. Найдено файлов с BOM: $count"

# Возврат к исходной папке
Pop-Location