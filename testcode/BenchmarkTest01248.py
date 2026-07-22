'''
OWASP Benchmark for Python v0.1

This file is part of the Open Web Application Security Project (OWASP) Benchmark Project.
For details, please see https://owasp.org/www-project-benchmark.

The OWASP Benchmark is free software: you can redistribute it and/or modify it under the terms
of the GNU General Public License as published by the Free Software Foundation, version 3.

The OWASP Benchmark is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
PURPOSE. See the GNU General Public License for more details.

  Author: Theo Cartsonis
  Created: 2025
'''

from flask import request, make_response

UPLOAD_DIR = "/tmp/benchmark-uploads"


def init(app):

    @app.route('/benchmark/fileupload-00/BenchmarkTest01248', methods=['GET', 'POST'])
    def BenchmarkTest01248():
        import os

        uploaded_file = request.files.get("file")
        os.makedirs(UPLOAD_DIR, exist_ok=True)

        if uploaded_file.content_type.startswith("image/"):
            uploaded_file.save(os.path.join(UPLOAD_DIR, uploaded_file.filename))
            return make_response("image uploaded")

        return make_response("not an image")

