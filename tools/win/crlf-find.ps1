# /tools/win/crlf-find.ps1
# --------------------------------------------------------------------
# Скрипт рекурсивно ищет в папке проекта файлы с нужными расширениями,
# проверяет наличие CRLF (Windows-переводы строк) и при обнаружении
# выводит список и общее количество таких файлов.
# --------------------------------------------------------------------
# Версия:    1.1
# Создано:   2025-04-24
# Обновлено: 2025-04-24
# Автор:     Александр Михайлов
# --------------------------------------------------------------------

# Переход к папке проекта
Push-Location -Path (Join-Path $PSScriptRoot "..\..")
Write-Host "Рекурсивный поиск файлов с CRLF (Windows-переводы строк) в папке: $PWD"

# Список расширений, для которых нужно выполнить проверку
$exts = @(".java", ".xml", ".properties", ".env", ".md", ".txt", ".sql", ".gitkeep", ".gitignore", ".gitattributes", ".editorconfig", ".yml", ".yaml", ".json")

# Счётчик проблемных файлов
$count = 0

# Рекурсивный обход файлов
Get-ChildItem -Recurse -File | ForEach-Object {
    $file = $_.FullName
    $ext = $_.Extension.ToLower()

    if ($exts -contains $ext) {
        try {
            $content = Get-Content -Path $file -Raw -Encoding UTF8
            if ($content -match "`r`n") {
                $count++
                Write-Host "Проблемный файл: $file"
            }
        } catch {
            Write-Warning "Не удалось прочитать файл: $file"
        }
    }
}

# Вывод результатов
Write-Host "Найдено проблемных файлов: $count"

# Возврат к исходной папке
Pop-Location
