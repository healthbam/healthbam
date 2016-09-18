INSERT INTO published_program (
        organization_name,
        mission,
        geo,
        summary,
        outcomes,
        public_email,
        public_phone,
        private_email,
        private_phone,
        created_on,
        created_by,
        updated_on,
        updated_by
) VALUES (
        'Existing Org 1',
        'To fill in some pre-existing data.',
        'geo is required info, but will not be this format',
        'Summary of report here?',
        'Info on outcomes here.',
        'public.john.doe@mailinator.com',
        '770-555-0000',
        'private.john.doe@mailinator.com',
        '404-555-9999',
        TIMESTAMP '2016-09-02 11:30:29',
        'initial data',
        NULL,
        NULL
);

INSERT INTO published_program (
        organization_name,
        mission,
        geo,
        summary,
        outcomes,
        public_email,
        public_phone,
        private_email,
        private_phone,
        created_on,
        created_by,
        updated_on,
        updated_by
) VALUES (
        'Existing Org 2',
        'To fill in more than one pre-existing data.',
        'geo is required info, but will not be this format',
        'Summary of report here?',
        'Info on outcomes here.',
        'public.jane.doe@mailinator.com',
        '770-555-0000',
        'private.jane.doe@mailinator.com',
        '404-555-9999',
        TIMESTAMP '2016-09-02 18:30:29',
        'initial data',
        TIMESTAMP '2016-09-05 21:30:29',
        'Ryan'
);


INSERT INTO requested_program (
        program_id,
        organization_name,
        mission,
        geo,
        summary,
        outcomes,
        public_email,
        public_phone,
        private_email,
        private_phone,
        created_on,
        created_by,
        updated_on,
        updated_by
) VALUES (
        NULL,
        'New Org Request 1',
        'To fill in a pre-existing request.',
        'geo is required info, but will not be this format',
        'Summary of report here?',
        'Info on outcomes here.',
        'public.jane.doe@mailinator.com',
        '770-555-0000',
        'private.jane.doe@mailinator.com',
        '404-555-9999',
        TIMESTAMP '2016-09-01 15:45:29',
        '127.0.0.1',
        TIMESTAMP '2016-09-05 21:30:29',
        'Ryan'
);
