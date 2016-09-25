INSERT INTO organization (
    name,
    contact_phone,
    contact_email,
    website_url,
    facebook_url,
    created_on,
    created_by
) VALUES (
    'Org 1',
    '770-555-1234',
    'org.one@mailinator.com',
    'http://www.org-one.org',
    'http://www.facebook.com/OrgOne',
    TIMESTAMP '2016-09-02 11:11:29',
    'initial data script'
);

INSERT INTO organization (
    name,
    contact_phone,
    contact_email,
    website_url,
    facebook_url,
    created_on,
    created_by
) VALUES (
    'Org 2',
    '770-555-1234',
    'org.two@mailinator.com',
    'http://www.org-two.org',
    'http://www.facebook.com/OrgTwo',
    TIMESTAMP '2016-09-04 12:20:29',
    'initial data script'
);

INSERT INTO program (
    name,
    organization_id,
    start_year,
    street_address,
    city,
    state,
    zip_code,
    created_on,
    created_by
) VALUES (
    'Program 1.1',
    (SELECT id FROM organization WHERE name = 'Org 1'),
    1999,
    '1006 North Highland Avenue Northeast',
    'Atlanta',
    'GA',
    '30306',
    TIMESTAMP '2016-09-05 11:59:29',
    'initial data script'
);

INSERT INTO program (
    name,
    organization_id,
    start_year,
    street_address,
    city,
    state,
    zip_code,
    created_on,
    created_by
) VALUES (
    'Program 1.2',
    (SELECT id FROM organization WHERE name = 'Org 1'),
    2001,
    '600 Chastain Rd NW #310',
    'Kennesaw',
    'GA',
    '30144',
    TIMESTAMP '2016-09-06 10:30:29',
    'initial data script'
);
